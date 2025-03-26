import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {
  Observable,
  throwError,
  BehaviorSubject
} from 'rxjs';
import {
  catchError,
  map,
  tap
} from 'rxjs/operators';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router
} from '@angular/router';

// Interfaces
export enum UserRole {
  ADMIN = 'ADMIN',
  HOMEOWNER = 'HOMEOWNER',
  MAINTENANCE = 'MAINTENANCE',
}

export enum RepairRequestStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  REJECTED = 'REJECTED'
}

export interface User {
  id?: number;
  name: string;
  email: string;
  role: UserRole;
  phone?: string;
  password?: string;
  houses?: House[];
}

export interface House {
  id?: number;
  address: string;
  ownerId?: number;
  ownerName?: string;
  ownerEmail?: string;
  repairRequests?: RepairRequest[];
}


export interface RepairRequest {
  id?: number;
  description: string;
  repairType?: string;
  status?: RepairRequestStatus;
  photoUrl?: string;
  comments?: string;
  houseId?: number;
  houseAddress?: string;
  createdById?: number;
  createdByName?: string;
  createdAt?: Date;
}


// Utility type for API responses
export interface ApiResponse<T> {
  data?: T;
  message?: string;
  error?: string;
}

// Pagination interface
export interface PaginatedResponse<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}

// Search and filter interfaces
export interface UserFilter {
  name?: string;
  email?: string;
  role?: UserRole;
}

export interface HouseFilter {
  address?: string;
  ownerId?: number;
}

export interface RepairRequestFilter {
  status?: RepairRequestStatus;
  houseId?: number;
  createdById?: number;
  startDate?: Date;
  endDate?: Date;
}

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface AuthResponse {
  success: boolean;
  message: string;
  user?: User;
}

@Injectable({
  providedIn: 'root'
})
export class MainService implements CanActivate {
  private apiBaseUrl = 'http://localhost:8090/api';

  // Authentication State
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.loadUserFromLocalStorage();
  }

  // Authentication Methods
  login(credentials: LoginCredentials): Observable<AuthResponse> {
    return this.http.post<User>(`${this.apiBaseUrl}/auth/login`, credentials)
      .pipe(
        tap(user => {
          // Ensure user is set in local storage and current user subject
          this.setCurrentUser(user);
        }),
        map(user => ({
          success: true,
          message: 'Login successful!',
          user: user
        })),
        catchError((error: HttpErrorResponse) => {
          let errorMessage = 'Login failed';

          if (error.error instanceof ErrorEvent) {
            // Client-side error
            errorMessage = error.error.message;
          } else {
            // Server-side error
            switch (error.status) {
              case 401:
                errorMessage = 'Invalid email or password';
                break;
              case 403:
                errorMessage = 'Access denied';
                break;
              default:
                errorMessage = error.error?.message || 'An unexpected error occurred';
            }
          }

          return throwError(() => new Error(errorMessage));
        })
      );
  }

  register(user: User): Observable<AuthResponse> {
    return this.http.post<User>(`${this.apiBaseUrl}/auth/register`, user)
      .pipe(
        map(registeredUser => ({
          success: true,
          message: 'Registration successful!',
          user: registeredUser
        })),
        catchError(this.handleError)
      );
  }

  logout(): Observable<AuthResponse> {
    return this.http.post(`${this.apiBaseUrl}/auth/logout`, {})
      .pipe(
        tap(() => {
          this.clearCurrentUser();
          window.location.href = '/login';
        }),
        map(() => ({
          success: true,
          message: 'Logout successful!'
        })),
        catchError(this.handleError)
      );
  }

  // User Management Methods
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiBaseUrl}/users`)
      .pipe(
        map(users => users.map(user => {
          const { password, ...userWithoutPassword } = user;
          return userWithoutPassword;
        })),
        catchError(this.handleError)
      );
  }

  deleteUser(userId: number | undefined): Observable<AuthResponse> {
    if (!userId) {
      return throwError(() => new Error('Invalid user ID'));
    }

    return this.http.delete(`${this.apiBaseUrl}/users/${userId}`, {
      observe: 'response'
    }).pipe(
      map(response => {
        if (response.status === 200) {
          return {
            success: true,
            message: response.body as string || 'User deleted successfully'
          };
        }
        throw new Error('Deletion failed');
      }),
      catchError(this.handleError)
    );
  }

  // House Management Methods
  getHouses(): Observable<House[]> {
    return this.http.get<House[]>(`${this.apiBaseUrl}/users/${this.getCurrentUser()?.id}/houses`);
  }

  getHouse(id: string | null): Observable<House> {
    return this.http.get<House>(`${this.apiBaseUrl}/houses/${id}`);
  }

// In MainService
  createHouse(houseData: { address: string }): Observable<House> {
    const currentUser = this.getCurrentUser();
    return this.http.post<House>(
      `${this.apiBaseUrl}/users/${currentUser?.id}/houses`,
      houseData
    );
  }
  updateHouse(id: string | null | undefined, house: House): Observable<House> {
    return this.http.put<House>(`${this.apiBaseUrl}/houses/${id}`, house);
  }

// In main.service.ts
  deleteHouse(houseId: number | undefined): Observable<ApiResponse<House>> {
    if (!houseId) {
      return throwError(() => new Error('Invalid house ID'));
    }

    return this.http.delete<House>(`${this.apiBaseUrl}/houses/${houseId}`, {
      observe: 'response'
    }).pipe(
      map(response => {
        if (response.status === 200) {
          return {
            success: true,
            message: 'House deleted successfully',
            data: response.body as House
          };
        }
        throw new Error('Deletion failed');
      }),
      catchError(this.handleError)
    );
  }

  // Repair Request Methods
  createRepairRequest(houseId: number, repairRequest: RepairRequest): Observable<RepairRequest> {
    const currentUser = this.getCurrentUser();
    return this.http.post<RepairRequest>(
      `${this.apiBaseUrl}/houses/${houseId}/repair-requests?userId=${currentUser?.id}`,
      repairRequest
    );
  }

  getHouseRepairRequests(houseId: number): Observable<RepairRequest[]> {
    return this.http.get<RepairRequest[]>(`${this.apiBaseUrl}/houses/${houseId}/repair-requests`);
  }

  getUserRepairRequests(): Observable<RepairRequest[]> {
    const currentUser = this.getCurrentUser();
    return this.http.get<RepairRequest[]>(`${this.apiBaseUrl}/users/${currentUser?.id}/repair-requests`);
  }

  // Authentication State Management
  setCurrentUser(user: User) {
    const userToStore = { ...user };
    delete userToStore.password;

    this.currentUserSubject.next(userToStore);
    localStorage.setItem('currentUser', JSON.stringify(userToStore));
  }

  clearCurrentUser() {
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
  }

  private loadUserFromLocalStorage() {
    const storedUser = localStorage.getItem('currentUser');
    if (storedUser) {
      const user = JSON.parse(storedUser);
      this.currentUserSubject.next(user);
    }
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.getValue();
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.getValue() !== null;
  }

  getUserRole(): string {
    const user = this.getCurrentUser();
    return user ? user.role : 'Guest';
  }

  // Role-based Access Control
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.isLoggedIn()) {
      // Optional: Add role-specific route guards
      const requiredRoles = next.data['roles'] as string[];
      if (requiredRoles) {
        const userRole = this.getUserRole();
        if (!requiredRoles.includes(userRole)) {
          // Redirect to unauthorized page or home
          this.router.navigate(['/unauthorized']);
          return false;
        }
      }
      return true;
    }

    // Redirect to login page
    this.router.navigate(['/login']);
    return false;
  }

  // Error Handling
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      switch (error.status) {
        case 200:
          // Treat 200 status as a success
          return throwError(() => ({
            success: true,
            message: error.error?.message || 'Operation successful'
          }));
        case 400:
          errorMessage = 'Invalid request. Please check your input.';
          break;
        case 401:
          errorMessage = 'Unauthorized. Please log in.';
          break;
        case 403:
          errorMessage = 'Forbidden. You do not have permission.';
          break;
        case 404:
          errorMessage = 'Resource not found.';
          break;
        case 500:
          errorMessage = 'Server error. Please try again later.';
          break;
        default:
          errorMessage = `Backend returned code ${error.status}, body was: ${error.error}`;
      }
    }

    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }

// In MainService
  getAllRepairRequests(): Observable<RepairRequest[]> {
    return this.http.get<RepairRequest[]>(`${this.apiBaseUrl}/repair-requests`);
  }

  getHouseManagement(): Observable<House[]> {
    return this.http.get<House[]>(`${this.apiBaseUrl}/houses/management`);
  }


}

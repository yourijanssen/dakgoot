import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

export interface User {
  id?: number;
  name: string;
  email: string;
  role: string;
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
export class AuthService {
  private apiUrl = 'http://localhost:8090/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserFromLocalStorage();
  }

  login(credentials: LoginCredentials): Observable<AuthResponse> {
    return this.http.post<User>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(user => this.setCurrentUser(user)),
        map(user => ({
          success: true,
          message: 'Login successful!',
          user: user
        })),
        catchError(this.handleError)
      );
  }



  register(user: User): Observable<AuthResponse> {
    return this.http.post<User>(`${this.apiUrl}/register`, user)
      .pipe(
        map(registeredUser => ({
          success: true,
          message: 'Registration successful!',
          user: registeredUser
        })),
        catchError(this.handleError)
      );
  }

// Add this method to the existing AuthService
  logout(): Observable<AuthResponse> {
    return this.http.post(`${this.apiUrl}/logout`, {})
      .pipe(
        tap(() => {
          // Clear current user
          this.clearCurrentUser();
          // Navigate to login page
          // You might want to inject Router here or use a different navigation method
          window.location.href = '/login';
        }),
        map(() => ({
          success: true,
          message: 'Logout successful!'
        })),
        catchError(this.handleError)
      );
  }

  setCurrentUser(user: User) {
    this.currentUserSubject.next(user);
    localStorage.setItem('currentUser', JSON.stringify(user));
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

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  deleteUser(userId: number | undefined): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete/${userId}`, { observe: 'response' })
      .pipe(
        map(response => {
          // Check if the response is successful
          if (response.status === 200) {
            console.log("delete succes")
            return {
              success: true,
              message: response.body || 'User deleted successfully'
            };
          }
          throw new Error('Deletion failed');
        }),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      switch (error.status) {
        case 200:
          // This is actually a success case, so we'll return a success message
          return throwError(() => ({
            success: true,
            message: 'Operation successful'
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
}

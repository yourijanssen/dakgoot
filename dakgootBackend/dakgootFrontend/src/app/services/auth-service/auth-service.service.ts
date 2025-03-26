// import { Injectable } from '@angular/core';
// import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// import { Observable, throwError, BehaviorSubject } from 'rxjs';
// import { catchError, map, tap } from 'rxjs/operators';
//
// export interface User {
//   id?: number;
//   name: string;
//   email: string;
//   role: string;
//   phone?: string;
//   password?: string;
// }
//
// export interface LoginCredentials {
//   email: string;
//   password: string;
// }
//
// export interface AuthResponse {
//   success: boolean;
//   message: string;
//   user?: User;
// }
//
// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private apiUrl = 'http://localhost:8090/api/auth';
//   private usersApiUrl = 'http://localhost:8090/api/users';
//   private currentUserSubject = new BehaviorSubject<User | null>(null);
//   currentUser$ = this.currentUserSubject.asObservable();
//
//   constructor(private http: HttpClient) {
//     this.loadUserFromLocalStorage();
//   }
//
//   login(credentials: LoginCredentials): Observable<AuthResponse> {
//     return this.http.post<User>(`${this.apiUrl}/login`, credentials)
//       .pipe(
//         tap(user => this.setCurrentUser(user)),
//         map(user => ({
//           success: true,
//           message: 'Login successful!',
//           user: user
//         })),
//         catchError(this.handleError)
//       );
//   }
//
//   register(user: User): Observable<AuthResponse> {
//     return this.http.post<User>(`${this.apiUrl}/register`, user)
//       .pipe(
//         map(registeredUser => ({
//           success: true,
//           message: 'Registration successful!',
//           user: registeredUser
//         })),
//         catchError(this.handleError)
//       );
//   }
//
//   logout(): Observable<AuthResponse> {
//     return this.http.post(`${this.apiUrl}/logout`, {})
//       .pipe(
//         tap(() => {
//           this.clearCurrentUser();
//           window.location.href = '/login';
//         }),
//         map(() => ({
//           success: true,
//           message: 'Logout successful!'
//         })),
//         catchError(this.handleError)
//       );
//   }
//
//   setCurrentUser(user: User) {
//     // Remove sensitive information before storing
//     const userToStore = { ...user };
//     delete userToStore.password;
//
//     this.currentUserSubject.next(userToStore);
//     localStorage.setItem('currentUser', JSON.stringify(userToStore));
//   }
//
//   clearCurrentUser() {
//     this.currentUserSubject.next(null);
//     localStorage.removeItem('currentUser');
//   }
//
//   private loadUserFromLocalStorage() {
//     const storedUser = localStorage.getItem('currentUser');
//     if (storedUser) {
//       const user = JSON.parse(storedUser);
//       this.currentUserSubject.next(user);
//     }
//   }
//
//   getCurrentUser(): User | null {
//     return this.currentUserSubject.getValue();
//   }
//
//   isLoggedIn(): boolean {
//     return this.currentUserSubject.getValue() !== null;
//   }
//
//   getUserRole(): string {
//     const user = this.getCurrentUser();
//     return user ? user.role : 'Guest';
//   }
//
//   getAllUsers(): Observable<User[]> {
//     return this.http.get<User[]>(`${this.usersApiUrl}`)
//       .pipe(
//         map(users => users.map(user => {
//           // Remove password from returned users
//           const { password, ...userWithoutPassword } = user;
//           return userWithoutPassword;
//         })),
//         catchError(this.handleError)
//       );
//   }
//
//   deleteUser(userId: number | undefined): Observable<AuthResponse> {
//     if (!userId) {
//       return throwError(() => new Error('Invalid user ID'));
//     }
//
//     return this.http.delete(`${this.usersApiUrl}/${userId}`, {
//       observe: 'response'
//     }).pipe(
//       map(response => {
//         if (response.status === 200) {
//           return {
//             success: true,
//             message: response.body as string || 'User deleted successfully'
//           };
//         }
//         throw new Error('Deletion failed');
//       }),
//       catchError(this.handleError)
//     );
//   }
//
//   private handleError(error: HttpErrorResponse) {
//     let errorMessage = 'An unknown error occurred!';
//
//     if (error.error instanceof ErrorEvent) {
//       // Client-side error
//       errorMessage = `Error: ${error.error.message}`;
//     } else {
//       // Server-side error
//       switch (error.status) {
//         case 200:
//           // Treat 200 status as a success
//           return throwError(() => ({
//             success: true,
//             message: error.error?.message || 'Operation successful'
//           }));
//         case 400:
//           errorMessage = 'Invalid request. Please check your input.';
//           break;
//         case 401:
//           errorMessage = 'Unauthorized. Please log in.';
//           break;
//         case 403:
//           errorMessage = 'Forbidden. You do not have permission.';
//           break;
//         case 404:
//           errorMessage = 'Resource not found.';
//           break;
//         case 500:
//           errorMessage = 'Server error. Please try again later.';
//           break;
//         default:
//           errorMessage = `Backend returned code ${error.status}, body was: ${error.error}`;
//       }
//     }
//
//     console.error(errorMessage);
//     return throwError(() => new Error(errorMessage));
//   }
// }

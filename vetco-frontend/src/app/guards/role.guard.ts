import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth/auth-service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const requiredRole = route.data['role'];
    const userRole = localStorage.getItem('role');

    if (userRole === requiredRole) {
      return true;
    } else {
      // ✅ Redirige vers le dashboard correspondant au rôle de l'utilisateur
      if (userRole === 'ADMIN') {
        this.router.navigate(['/dashboard/admin']);
      } else if (userRole === 'SUPER_MANAGER') {
        this.router.navigate(['/dashboard/super-manager']);
      } else if (userRole === 'MANAGER') {
        this.router.navigate(['/dashboard/manager']);
      } else if (userRole === 'USER') {
        this.router.navigate(['/dashboard/user']);
      } else {
        // Si pas de rôle, on déconnecte vers login
        this.router.navigate(['/login']);
      }
      return false;
    }
  }
}

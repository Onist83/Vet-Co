import { Routes } from '@angular/router';
import { AnimalsListComposant } from './components/animals-list-composant/animals-list-composant';
import { DashboardAdminComposant } from './components/dashboard-admin-composant/dashboard-admin-composant';
import { DashboardUserComposant } from './components/dashboard-user-composant/dashboard-user-composant';
import { Login } from './components/login-composant/login-composant';
import { RegisterAnimalComposant } from './components/register-animal-composant/register-animal-composant';
import { UsersListComposant } from './components/users-list-composant/users-list-composant';
import { ViewInfosAnimalComposant } from './components/view-infos-animal-composant/view-infos-animal-composant';
import { ViewInfosUserComposant } from './components/view-infos-user-composant/view-infos-user-composant';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

export const routes: Routes = [
  { path: 'login', component: Login },
  {
    path: 'dashboard/admin',
    component: DashboardAdminComposant,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ADMIN' },
  },

  {
    path: 'dashboard/admin/animals',
    component: AnimalsListComposant,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ADMIN' },
  },

  {
    path: 'dashboard/admin/veterinarians',
    component: UsersListComposant,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ADMIN' },
  },

  {
    path: 'animal/:id/view',
    component: ViewInfosAnimalComposant,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ADMIN' },
  },

  {
    path: 'dashboard/veterinarian',
    component: DashboardUserComposant,
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'VETERINARIAN' },
  },

  {
    path: 'veterinarian/:id/view',
    component: ViewInfosUserComposant,
    canActivate: [AuthGuard],
  },

  { path: 'animal/:id/view', component: ViewInfosAnimalComposant, canActivate: [AuthGuard] },

  { path: 'register-animal', component: RegisterAnimalComposant, canActivate: [AuthGuard] },

  { path: 'register-animals/:id', component: RegisterAnimalComposant, canActivate: [AuthGuard] },

  { path: 'animal/:id/view', component: ViewInfosAnimalComposant, canActivate: [AuthGuard] },

  { path: 'profil', component: ProfileVeterinarianComposant, canActivate: [AuthGuard] },

  { path: 'commentary', component: Commentary, canActivate: [AuthGuard] },

  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];

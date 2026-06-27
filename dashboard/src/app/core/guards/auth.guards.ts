import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map, catchError, of } from 'rxjs';
import { AuthStateService } from '../services/auth-state.service';
import { AccountApiService } from '../services/account-api.service';

export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthStateService);
  const router = inject(Router);

  if (auth.isAuthenticated) {
    return true;
  }

  return router.createUrlTree(['/login']);
};

export const guestGuard: CanActivateFn = (route) => {
  const auth = inject(AuthStateService);
  const router = inject(Router);

  if (!auth.isAuthenticated) {
    return true;
  }

  if (route.routeConfig?.path === 'register') {
    return router.createUrlTree(['/criar-conta']);
  }

  return router.createUrlTree(['/']);
};

export const onboardingGuard: CanActivateFn = () => {
  const accounts = inject(AccountApiService);
  const router = inject(Router);

  return accounts.findAll().pipe(
    map((items) =>
      items.length > 0 ? true : router.createUrlTree(['/criar-conta']),
    ),
    catchError(() => of(router.createUrlTree(['/criar-conta']))),
  );
};

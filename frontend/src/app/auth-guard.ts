import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { Observable } from 'rxjs';

export class AuthGuard implements CanActivate {

  constructor(private snackBar: MatSnackBar) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }

}

import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable, of } from "rxjs";
import { IPeticion } from "./iPeticion"
import { map, tap, catchError } from "rxjs/operators";
import { HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
  })
export class PeticionesService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  peticionsUrl = "http://localhost:8080/peticiones";
  itemsUrl = "http://localhost:8080/item-peticion";
  
    constructor(private http: HttpClient) {        
    }

    public getPeticiones()
    {
         return this.http.get<IPeticion[]>(this.peticionsUrl)
        .pipe(
          tap(_ => console.log('extrayendo peticiones')),
        catchError(this.handleError<IPeticion[]>('loadPeticion', []))
      );
    }
    
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {      
          // TODO: send the error to remote logging infrastructure
          console.error(error); // log to console instead      
          // TODO: better job of transforming error for user consumption
          console.log(`${operation} failed: ${error.message}`);      
          // Let the app keep running by returning an empty result.
          return of(result as T);
        };
      }

      /** POST: add a new Peticion to the server */
  public agregaPeticion(Peticion: IPeticion): Observable<IPeticion>  {
    return this.http.post<IPeticion>(this.itemsUrl, Peticion, this.httpOptions).pipe(
      tap((newPeticion: IPeticion) => console.log(`added Peticion w/ id=${newPeticion.id}`)),
      catchError(this.handleError<IPeticion>('addPeticion'))
    );
  }



}

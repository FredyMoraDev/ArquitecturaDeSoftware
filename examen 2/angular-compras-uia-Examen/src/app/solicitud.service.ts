import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { BehaviorSubject, Observable, of } from "rxjs";
import { ISolicitud } from "./iSolicitud"
import { map, tap, catchError } from "rxjs/operators";
import { HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
  })
export class SolicitudService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  solicitudsUrl = "http://localhost:8080/solicitudesOC";
  itemsUrl = "http://localhost:8080/item-solicitud";
  private solicitudes$ = new BehaviorSubject<ISolicitud[]>([]);

    constructor(private http: HttpClient) {        
    }

    public getSolicitudes(id:number)
    {
        this.solicitudsUrl = "http://localhost:8080/solicitudesOC"+`?id=`+id;
        this.http.get<ISolicitud[]>(this.solicitudsUrl).subscribe((pozos) => this.solicitudes$.next(pozos));
        return this.solicitudes$;
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

      /** POST: add a new Solicitud to the server */
  public agregaSolicitud(Solicitud: ISolicitud): Observable<ISolicitud>  {
    return this.http.post<ISolicitud>(this.itemsUrl, Solicitud, this.httpOptions).pipe(
      tap((newSolicitud: ISolicitud) => console.log(`added Solicitud w/ id=${newSolicitud.id}`)),
      catchError(this.handleError<ISolicitud>('addSolicitud'))
    );
  }



}
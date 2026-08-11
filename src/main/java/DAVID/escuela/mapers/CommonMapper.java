package DAVID.escuela.mapers;

public interface CommonMaper<RQ, RS ,E> {
 E requestAEntidad(RQ request);

 RS entidadAResonse(E entidad);
}


package DAVID.escuela.mapers;

public interface CommonMapper<RQ, RS ,E> {
 E requestAEntidad(RQ request);

 RS entidadAResponse(E entidad);
}


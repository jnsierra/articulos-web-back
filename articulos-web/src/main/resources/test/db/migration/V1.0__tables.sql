CREATE TABLE id_articulo
 (
              id INT8 NOT NULL,
              created_by VARCHAR(255),
              created_date TIMESTAMP,
              last_modified_by VARCHAR(255),
              last_modified_date TIMESTAMP,
              contenido       VARCHAR(255),
              estado          VARCHAR(255),
              resumen_espanol VARCHAR(255),
              resumen_ingles  VARCHAR(255),
              idea_id INT8 NOT NULL,
              PRIMARY KEY (id)
 );

 CREATE TABLE id_comentario
 (
              id INT8 NOT NULL,
              created_by VARCHAR(255),
              created_date TIMESTAMP,
              last_modified_by VARCHAR(255),
              last_modified_date TIMESTAMP,
              comentario VARCHAR(255),
              articulo_id INT8 NOT NULL,
              PRIMARY KEY (id)
 );

 CREATE TABLE id_ideas
 (
              id INT8 NOT NULL,
              created_by VARCHAR(255),
              created_date TIMESTAMP,
              last_modified_by VARCHAR(255),
              last_modified_date TIMESTAMP,
              contenido VARCHAR(2500),
              estado    VARCHAR(255),
              fecha_aprob TIMESTAMP,
              profesor_id INT8 NOT NULL,
              id_prof_autoriza INT8,
              titulo VARCHAR(300),
              usuario_id INT8 NOT NULL,
              PRIMARY KEY (id)
 ) ;

 CREATE TABLE us_usuario
 (
              id INT8               NOT NULL,
              created_by            VARCHAR(255),
              created_date          TIMESTAMP,
              last_modified_by      VARCHAR(255),
              last_modified_date    TIMESTAMP,
              cambio_contra         VARCHAR(255) NOT NULL,
              password              VARCHAR(255) NOT NULL,
              email                 VARCHAR(255) NOT NULL,
              NAME                  VARCHAR(255) NOT NULL,
              tipo_usuario          VARCHAR(255),
              PRIMARY KEY (id)
 );
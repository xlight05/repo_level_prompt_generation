================================================================================
Documentación asociada al framework de desarrollo JGermen-RCP

En este documento se especifícan todos los puntos y aspectos de desarrollo
que se han tenido en cuenta para crear este framework de partida para
abordar los desarrollos técnicos sobre interfases de interacción humana (UI).

Este documento ha sido elaborado por Roberto Crespo Panizo
versión 0.1 2009.
================================================================================

1.- DESCRIPCIÓN DE HERRAMIENTAS

Los frameworks de apoyo al desarrollo se basan en plataformas opensource que
se detallan a continuación,

    a) IDE de desarrollo NetBeans.
    b) Plataforma de desarrollo basada en J2EE
    c) Servidores de aplicaciones basados en la tecnología del punto b)
    d) FrameWork Ajax Extjs (versión 2.0 bajo licencia GPL)
    e) FrameWork de librerías tld Exttld (bajo licencia GPL)
    f) FrameWork de generación de gráficos basado en librería tld Cewolf.

Para completar el conjunto de herramientas que se utilizarán para el desarrollo
de aplicaciones de UI se creará una librería J2EE que contendrá las bases
necesarias para ejecutar las acciones que se solicitarán desde la parte visual
de la aplicación. Todo este conjunto de objetos determinará tanto el acceso
a datos (SELECT, UPDATES, DELETES, INSERTS) como acciones genéricas de control
de acceso y configuración.

2.- BASE DE ACCESO A INFORMACIÓN

Para establecer la base de acceso a la información se establecerán dos objetos
que contendrán toda la lógica de ejecución de acciones de base de datos. Para
ello se crearán los objetos siguientes,

    a) Interfase 'InterBaseDatos'

        Incorporará las funciones genéricas que deberá implementar la clase
        base de acceso a la información.

    b) Clase Base 'ClassBaseDatos'

        Este objeto implementará todos los métodos que obtiene de la interfase
        InterBaseDatos.

        A este objeto se le tendrá que administrar toda la información
        necesaria para poder realizar las acciones solicitadas. Se programará
        de forma genérica donde por parámetro se le indicará lo que ha de hacer
        y retornará un objeto genérico JSONObject que contendrá el resultado.

Cualquier acción que se haya de implementar tendrá que extender la clase base
ClassBaseDatos. El objeto que implemente la acción tendrá en cuenta los datos
siguientes,

    a) Parámetro indicando la acción a ejecutar. Identificará la clave del
    fichero de configuración que tendrá que ejecutar.
    b) Parámetro indicando los valores a tener en cuenta para la sustitución
    de estos en la acción del punto a)
    c) Parámetros para rango de datos en caso de que la acción sea de tipo
    SELECT.

3.- GESTIÓN DE ACCIONES

Todas las acciones serán gestionadas por un objeto genérico que será capáz de
discriminar dependiendo de ciertos parámetros la unidad de negocio que solicita
los recursos, la acción a realizar y los valores que se deben utilizar en la
acción requerida. La descripción de este objeto será

    a) Clase 'ClassActionManager'
    b) Implementación de métodos para ejecutar una acción genérica pasada por
    parámetros en la función 'runAction()'. Esta llamada retornará un objeto
    genérico de tipo JSONObject que se pasará directamente al llamante.

4.- PETICIONES DE RECURSOS

Cada una de las unidades de negocio implementadas para la capa de UI, i.e,
modelo visual de la aplicación, será resuelta por un objeto único de tipo
servlet que desribimos a continuación

    a) Servlet de gestión de peticiones ServletActionManager
    b) Se le pasarán todos los parámetros necesarios para que pueda realizar la
    acción con garantías y con toda la información que necesita. Para ello los
    parámetros que se le han de pasar son los siguientes,

        OBLIGATORIOS,

        FM_UUID      : Indicará el nombre de la unidad de negocio llamante.
        AM_UUID      : Clase action manager para la gestión de la solicitud.
        DB_UUID      : Database manager para la gestión de acceso a datos.
        _action      : Indicará la acción que se solicita se ejecute.
        _json_params : Indicará los parámetros que se deberán tener en cuenta
                       para realizar la acción indicada. Este dato estará
                       formateado como JSONObject.

        OPCIONALES,

        _start       : Indicará el registro de comienzo para el retorno de los
                       datos que se están solicitando.
        _limit       : Indicará el límite del montante de datos que se han de
                       enviar al llamante.

Las llamadas al servlet manager por defecto se ha de efectuar mediante la url

  /exec.mrwl?

5.- PERMISOS Y CONTROL DE ACCESO

Se cargará un objeto en la sesión de usuario que indicará el conjunto de
permisos y accesos a acciones disponibles en la aplicación. Este objeto
recuperará los datos en el inicio de sesión en función de la configuración
establecida en la base de datos.

6.- CONFIGURACION DE ACCIONES

Cada acción que se deba realizar mediante la llamada al servlet manager estará
ubicada en un fichero de 'properties' nombrado con las reglas siguientes,

     <UNIDAD DE NEGOCIO>-<ACCION>.properties

     Ejemplo. El siguiente ejemplo identifica los datos de configuración de la
     unidad de negocio UD0001 y la acción SELECT-01. Puede darse el caso que
     para una misma unidad de negocio existen varias acciones de tipo SELECT y
     por lo tanto se enumeran de 1 a 99. El caso más típico puede ser un
     maestro detalle.

     UD0001-SELECT-01.properties

Todos los ficheros de configuración se deben ubicar en el paquete

    'com.mrwlogistica.model'








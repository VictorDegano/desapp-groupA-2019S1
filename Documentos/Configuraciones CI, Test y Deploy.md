#**Configuracion del Proyecto**

Que se configuro 
1. Travis CI
2. Codacy
3. JaCoCo
4. Heroku


##Configuracion de Travis CI

Para poder tener la integracion con Travis tenemos que crear el archivo **`.travis.yml`** en el directorio raiz del proyecto. Dentro de el se hara las configuraciones que cargara travis cuando hagamos un push a la rama master.

**¿Que se configuro en travis?**
- [x] Notificaciones por mail
- [x] Testeo
- [x] Deploy a Heroku


######++Notificaciones por mail:++
Con esta configuracion podremos indicar cuando, quienes y por que medio se notificara el estado de travis ante una build. Por defecto travis envia un mail al dueño de la cuenta tras terminar la build.

Nosotros configuramos las notificaciones para que solo envie un mail cuando una build falla, y este se enviara a los colaboradores del proyecto. 

Fragmento de configuracion de las notificaciones:
```
notifications:
  email:
    recipients:
      - flame.el22@gmail.com
      - ivanjaratamargo@gmail.com
      - dominikowivan@gmail.com
    on_success: never # default: change
    on_failure: always # default: always```

La palabra `notifications` indica que vamos a realizar la configuracion de las notificaciones. Estas pueden ser realizadas por IRC, Email, Slack, etc. ([Mas informacion sobre notificaciones](https://docs.travis-ci.com/user/notifications/))

En nuestro caso configuramos para que se notifique por Email, esto se hace mediante la palabra `Email`. Los destinatarios de definen con `recipients`, el cual espera un bloque de correos.
Por ultimo para configurar cuando queremos que se nos notifique, esto se hace con las palabras `on_success` y `on_faliure`. La frecuencia de notificacion puede ser `never, always, change`. En nuestro caso no nos interesa cuando una build pasa, pero si cuando falla; asi que dejamos configurado `on_success: never` y `on_faliure: always`.

######++Testeo y Deploy++

El testeo y el deploy se puede hacer todo junto, pero nosotros decidimos separarlo en partes, esto se hace mediante la palabra `jobs`([Mas informacion sobre notificaciones](https://docs.travis-ci.com/user/job-lifecycle/)). Con esta palabra le indicamos a travis que se va a realizar diferentes trabajos (La redundancia es gratis).
Los trabajos en travis se pueden separar en tres partes: la etapa de instalacion, la etapa de Build y la estapa de deploy (Esta ultima es opcional).

El jobs espera recibir los trabajos a ejecutar, los cuales se agregan mediante la palabra `include` que recibe un bloque con `stage` que son los diferentes trabajos (separados por `-` e identados). 

Fragmento de codigo de configuracion de los trabajos:
```
jobs:
  include:
    -
      stage: Test
      language: java
      jdk: oraclejdk11
      before_install:
        - "sudo apt-get install jq"
        - "wget -O ~/codacy-coverage-reporter-assembly-latest.jar $(curl https://api.github.com/repos/codacy/codacy-coverage-reporter/releases/latest | jq -r .assets[0].browser_download_url)"

      before_script:
        - "cd desapp-groupA-2019S1-backend"
        - "mvn clean install -Dmaven.compiler.target=1.8 -Dmaven.compiler.source=1.8 -Dmaven.javadoc.skip=true -B -V"
      script:
        - "mvn test -B"

      # Para enviar el reporte de jacoco a codacy
      after_success:
        - "java -jar ~/codacy-coverage-reporter-assembly-latest.jar report -l Java -r target/jacoco-report/jacoco.xml"

    -
      stage: "Deploy Back-end"
      language: java
      jdk: oraclejdk11
      script: 
        - "cd desapp-groupA-2019S1-backend"
        - "mvn clean install -DskipTests=true"
      deploy:
        provider: heroku
        skip_cleanup: true
        api_key:
          secure: $HEROKU_API_KEY
        app: desapp-grupoa-2019s1-backend
        strategy: api

    -
      stage: "Deploy Front-end"
      language: node_js
      node_js: "10"
      cache: npm
      before_install:
        - "cd desapp-groupA-2019S1-frontend"
      install:
        - npm install
      script: true
      deploy:
        provider: heroku
        skip_cleanup: true
        api_key:
          secure: $HEROKU_API_KEY
        app: desapp-grupoa-2019s1-frontend
        strategy: api
```
Como se ve, hay bastante configuracion. Pero para que se entienda la idea de jobs, se define asi:

```
jobs:
  include:
    - Stage 1
    - Stage 2
    - Stage ...
``` 

En cada stage se puede configurar cosas como el lenguaje, instalar paquetes, cache de directorios, ejecucion de script, etc.

*++Etapa Test++*
Para el test se realizo la siguiente configuracion:
- El nombre del trabajo mediante `stage`
- Se especifico el lenguaje Java mediante `language`
- Para este caso se indico que version de jdk se utiliza (Se puede poner mas de 1), mediante `jdk`
- Se indico en el `before_install` que se instale el paquete de reporte de covertura de codacy (Codacy se vera mas adelante).
- Se instalo las dependencias de maven con `before-script` el cual ejecuta comandos antes de que se haga lo especificado en el `script`. Antes de instalar las dependencias se hizo un `cd` para moverse a la carpeta de backend y asi poder instalar las dependencias
- Con el comando `script` se ejecuto los test
- Para finalizar con `after_success`(El cual solo se ejecutara si el comando script, el cual ejecuta lo test, termina correctamente), se enviara a codacy el reporte de cobertua de codigo generado por JaCoCo (Se vera mas adelante que es JaCoCo y como configurarlo)

*++Etapa de Deploy de Backend++*
En esta estapa se definio como va a ser el deploy de nuestra aplicacion backend, el cual se va a hacer contra heroku(ya veremos mas adelante como configurar heroku). Para las configuraciones de lenguaje, jdk y nombre de stage se pusieron las mismas que para la etapa test.

Ademas de eso se configuro:
- En `script` se movio a la carpeta dle backend y se realizo un clean instal de las dependencias de maven salteandose los test.
- Con la palabra `deploy`, definimos como vamos a deployar la aplicacion: 	
	- Especificamos a heroku como proveedor mediante `provider` 
	- Indicamos a travis que no limpie archivos adicionales (Por defecto lo hace), mediante `skip_cleanup`
	- Para poder hacer el deploy en heroku, necesitamos una API Key (En la seccion de heroku se vera como obtenerla) el cual configuramos con el comando `api_key`. Y en este caso la API key esta guardada en la variable $HEROKU_API_KEY que se encuentra en travis (Mas adelante se vera como definir esta variable)
	- Definimos que aplicacion de heroku es donde vamos a deployar con el comando `app`
	- Por ultimo definimos como hacemos el deploy (Puede ser por git o por api) mediante el comando `strategy`

*++Etapa de Deploy de Frontend++*
En esta etapa se hizo la configuracion para el deploy del frontend. Como la configuracion es parecida a la del backend (En especial el deploy, que lo unico que cambia es a que aplicacion de heroku se deployara), solo se explicara lo que difiera.

Configuracion:
- En `lenguage` se definio a node_js
- Especificamos la version de node con el comando `node_js`
- Se indico que se ponga en cache las carpetas que utiliza node mediante `cache: npm`
- En el `before_install` solo se cambio a la carpeta del proyecto de frontend.
- En `install` se instalaron las dependencias del frontend (Definidas en el package.json del proyecto)
- Como no hay de momento test para el frontend, se definio `script: true` para que pueda hacer el deploy

######++Como agregar variables de entorno en travis++
Hacer esto es sencillo, vamos a la pagina de travis y entramos al repositorio en el cual queremos definir la variable. En la pestaña `Settings` bajamos hasta el apartado *Environment Variables* y ahi podemos definir un nombre para la variable y su valor. Tras completar esos campos, con add, lo agregamos al repositorio y para acceder solo se tiene que llamar a la variable con `$` (Como se hace en la configuracion para la key de heroku).

##Configuracion de Codacy
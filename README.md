# Desapp-C1-2019-ACaraDePerro

## Integrantes
- - -
   Nombre            |						Usuario						| Legajo UNQ |							Email
---------------------|--------------------------------------------------|------------|----------------------------------------------------------
Ivan Diego Dominikow |[@Dominikowivan](https://github.com/Dominikowivan)|    37517   | [dominikowivan@gmail.com](mailto:dominikowivan@gmail.com)
Victor Mariano Degano|[@VictorDegano](https://github.com/VictorDegano)  |    26106   | [flame.el22@gmail.com](mailto:flame.el22@gmail.com)
Ivan Jara            |[@jaraivan](https://github.com/jaraivan)          |    33389   | [ivanjaratamargo@gmail.com](mailto:ivanjaratamargo@gmail.com)
## Estado del Proyecto

###### Travis
[![Build Status](https://travis-ci.com/VictorDegano/desapp-groupA-2019S1.svg?token=drGKKQpy8a3rz3mat6Cb&branch=master)](https://travis-ci.com/VictorDegano/desapp-groupA-2019S1) Master Branch

###### Codacy
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/47b2d81337374aed89c69ab5251f75ee)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=VictorDegano/desapp-groupA-backend&amp;utm_campaign=Badge_Grade) Master Branch


## Enunciado

#### Eventeando

Hoy en día es común encontrar sitios o aplicaciones que nos ayudan a organizar un encuentro con amigos, una fiesta o cualquier otro tipo de evento donde sea necesario conocer la cantidad de asistentes, las cantidades a comprar, el monto a pagar por cada asistente, etc.

Eventeando busca crear una nueva aplicación donde no solo se puedan crear eventos, invitar asistentes, calcular las comprar, dividir gastos y más, sino también contar con una cuenta corriente que permita a los usuarios ingresar dinero a través de distintos medios, solicitar micro préstamos o prestar dinero a sus amigos, por ejemplo.

Con eventeando se podrán organizar tres tipos de eventos, los cuales se describen a continuación:

- Fiesta: En este caso se envían invitaciones a través de la aplicación y por cada confirmación de asistencia, la aplicación va calculando la cantidad de mercaderías a comprar. En este tipo de eventos no se distribuyen los gastos del mismo sino que corren por cuenta del organizador. Al momento de crear el evento, el organizador no sólo ingresa una lista de usuarios a los cuales invitar sino también hasta cuanto tiempo antes se admiten confirmaciones.

- Baquita: En este caso se determina el evento a realizar y sus gastos (comida, alquiler de salon, etc) y a partir de la cantidad de asistentes la aplicación determina cuánto debe pagar cada uno de ellos.
La baquita puede tener dos modalidades. En la primera, una o más personas realizan las compras, informan lo gastado y luego se divide con los demás asistentes al evento. En la segunda, la aplicación calcula un costo estimado total para el evento (se puede valer de información propia o ingresada por los usuarios), crea una cuenta común a la cual deberán girar su parte los asistentes y una vez reunido el dinero, el organizador puede disponer de los msmos para las compras.

- Canasta: Este es un tipo de evento similar a la baquita, pero la diferencia radica en que en lugar de dividir los gastos en cuotas iguales a pagar por los asistentes, se presenta la lista de gastos a realizar y, al mejor estilo scrum meeting, cada asistente elige un ítem del cual hacerse cargo.

Adicionalmente, para cada tipo de evento la aplicación debe permitir configurar templates, los cuales pueden ser elegidos por los usuarios al momento de crear un evento, de modo de facilitar su organización. Por ejemplo, un template para asado con amigos en modalidad canasta, podría contener elementos tales como bolsa de carbon, asado, vacio, vino, tomate, lechuga y pan.

Al momento que un usuario selecciona un template, debe poder visualizar una descripción general del mismo y tener la posibilidad de visualizar el detalle de los elementos que este contiene. La aplicación no solo debe mostrar la descripción general del template sino también listar aquellos otros templates que hayan sido utilizados por otros usuarios que eligieron el template seleccionado. (el clasico, quien compró esto también compró esto otro)

Los usuarios que deseen utilizar eventeando deberán registrarse en la aplicación, para lo cual será necesaria la siguiente información:
1. Nombre: Texto -  Max 30 -  Requerido.
2. Apellido: Texto -  Max 30 -  Requerido.
3. Email: Formato_email - Requerido
4. Contraseña: Min 4 - Max 10 - Alfanumérico - Requerido.
5. Fecha de Nacimiento : DD/MM/AAAA - Requerido

También será necesario ofrecer un sistema de autenticación mediante auth0 con cuentas de Gmail.

Las invitaciones a eventos se realizarán enviando correos electrónicos a los participantes, los cuales deberán registrarse en la aplicación para poder tomar parte del evento en cuestión.

Para ingresar dinero en la aplicación, la operación puede realizarse en efectivo en un punto de carga similar a los utilizados para pagos de servicios o puede hacerse utilizando una tarjeta de crédito. La aplicación debe llevar el control de los movimientos realizados por el usuario con el objetivo de poder listar su estado de cuenta cuando sea requerido. Por cada movimiento se debe conocer mínimamente el tipo, la fecha y el monto.

Para poder retirar dinero de la aplicación, se podrá solicitar el efectivo en los mismos centros de carga o solicitar la acreditación en la tarjeta de crédito, esto último se realiza a través de la aplicación.

Por último, la aplicación contempla la posibilidad de otorgar micro créditos a aquellos usuarios que hayan demostrado ser cumplidores. Ser cumplidor implica que en al menos 3 de los últimos eventos en que participó (baquita y/o canasta), cumplió en término con su participación. Si el usuario es cumplidor y no posee un préstamo en curso, podrá solicitar $1000 a retornar en 6 cuotas mensuales de $200 cada una. Las cuotas se debitan automáticamente de la cuenta del usuario el día 5 de cada mes. Si el usuario no posee el dinero suficiente, la cuota queda pendiente hasta que existan fondos y el usuario pasa a ser moroso.

Desde la administración de eventeando, se desea poder acceder al listado de créditos en curso y su situación. (usuario solicitante, cuotas restantes, morosidad)

La home de la aplicación deberá mostrar mis eventos en curso, mis últimos eventos y los eventos mas populares.

La aplicación debe ser completamente responsibe ya que estará accesible desde múltiples dispositivos.

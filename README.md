# INT002-WS_APIREST

Repositorio para servicios REST de la API de negocio

se debe generar la shell /u01/reembolso/kill.sh en servidor de destino de las apis

export PID=`ps aux --sort -rss | head | grep $1 | awk '{print $2}'`
if [ "${PID}" == "" ] ; then
    echo "$1 no iniciado"
else
    kill -9 ${PID}
    echo "$1 terminado"
fi


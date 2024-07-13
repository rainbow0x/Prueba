# Dockerfile

# Usa una imagen de Python como base
#FROM python:3.9-slim

# Establece el directorio de trabajo en /app
#WORKDIR /app

# Copia el archivo `app.py` al contenedor en `/app`
#COPY src/app.py .

# Instala Flask dentro del contenedor
#RUN pip install Flask

# Expone el puerto 5000 del contenedor
#EXPOSE 5000

# Define el comando por defecto para ejecutar tu aplicación
#CMD ["python", "src/app.py"]




FROM alpine:3.10

RUN apk add --no-cache python3-dev \
   && pip3 install --upgrade pip
#RUN pip install Flask

WORKDIR /app

COPY . /app

RUN pip3 --no-cache-dir install Flask

CMD [ "python3", "src/app.py" ]
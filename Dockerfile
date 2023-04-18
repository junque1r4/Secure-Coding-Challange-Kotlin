# Use the latest mariadb image as the base image
FROM mariadb:latest

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=mypass
ENV MYSQL_DATABASE=SecureCards

# Copy the init.sql file into the container
COPY init.sql /docker-entrypoint-initdb.d/

# Expose the port for external access
EXPOSE 3306

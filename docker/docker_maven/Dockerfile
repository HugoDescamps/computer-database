FROM java:8 

# Install maven
RUN apt-get update
RUN apt-get install -y maven

#/workspace is a volume shared by jenkins container
WORKDIR /workspace

# Prepare by downloading dependencies
ENTRYPOINT ["mvn", "dependency:resolve"]
ENTRYPOINT ["mvn", "verify"]

#Tests and build
ENTRYPOINT ["mvn","clean","package"]

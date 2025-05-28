DIR_ATUAL=$( pwd )
cp ${DIR_ATUAL}/target/financeiro-0.0.1-SNAPSHOT.jar .
docker build --no-cache -t financeiro-service .

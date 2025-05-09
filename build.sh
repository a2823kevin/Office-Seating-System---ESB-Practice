# clean up
rm -rf frontend/dist
rm -rf backend/src/main/resources/static/*
rm -rf backend/src/main/resources/DB


# build frontend
cd frontend
npm run build

# copy built resource & DB DDL/DML to backend
cd ..
cp -R frontend/dist/* backend/src/main/resources/static/
cp -R DB backend/src/main/resources/

# build backend
cd backend
mvn clean package -DskipTests

# export built jar
cp target/*.jar ../server.jar
import base.connect as c
connection = c.Connect("eriq66", "root", "postgis_nyc")
# connection.insert("insert into routes values(default, 'Autre', 'POINT(-10.905777 47.524886)')")
print(connection.select("select idroute, nomroute, ST_X(coords) from routes"))
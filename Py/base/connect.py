import psycopg2
class Connect:
    def __init__(self, username, password, dbname):
        self.username=username
        self.password=password
        self.dbname=dbname
    
    def select(self, query):
        conn= psycopg2.connect(f"dbname={self.dbname} user={self.username} password={self.password}")
        cur=conn.cursor()
        try:
            cur.execute(query)
            list=cur.fetchall()
            cur.close()
            return list
        except(Exception) as e:
            print(e)
        finally:
            cur.close()
            conn.close()
    
    def insert(self, query):
        conn= psycopg2.connect(f"dbname={self.dbname} user={self.username} password={self.password}")
        cur=conn.cursor()
        try:
            cur.execute(query)
            conn.commit()
        except(Exception) as e:
            conn.rollback()
            print(e)
        finally:
            cur.close()
            conn.close()
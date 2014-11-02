import sys, getopt
import mysql.connector
import csv

def readFile(filename):
  retval = []
  with open(filename, 'rb') as tsvin:
    tsvin = csv.reader(tsvin, delimiter='\t')
    for row in tsvin:
      if row:
        retval.append(tuple(row))
  return retval

def insertTech(cursor,data):
  add_tech = 'INSERT INTO TechTree VALUES(%s, %s, %s, %s)'
  cursor.execute(add_tech,data)
  

def main(argv):
  config = {
  'user':'',
  'password':'',
  'host':'',
  'database':'',
  }
  filename =''

  try:
    opts, args = getopt.getopt(argv, "h:u:p:d:f:")
  except getopt.GetoptError:
    print "dbentry.py -h <hostname> -u <username> -p <password> -d <database> -f <tsv_filename>" 
    sys.exit(0)
  for opt,arg in opts:
    if opt == '-h':
      config['host'] = arg
    elif opt == '-u':
      config['user'] = arg
    elif opt == '-p':
      config['password'] = arg
    elif opt == '-d':
      config['database'] = arg
    elif opt == '-f':
      filename = arg

  if filename == "":
    print "dbentry.py -h <hostname> -u <username> -p <password> -d <database> -f <tsv_filename>" 
    sys.exit(0)

  techlist = readFile(filename)

  try:
    cnx = mysql.connector.connect(**config)
  except mysql.connector.Error as e:
    print (e)  
    sys.exit(0)
  
  cursor = cnx.cursor() 

  for tech in techlist:
    insertTech(cursor, tech)

  cnx.commit()
  cursor.close()
  cnx.close()

if __name__ == "__main__":
  main(sys.argv[1:])

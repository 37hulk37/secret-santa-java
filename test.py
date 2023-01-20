import requests

r = requests.post('http://127.0.0.1/register-user', data={"name": "Jason Burne"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.post('http://127.0.0.1/create-group', data={
  "name": "Jason Bourne",
          "group_name": "Agents"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

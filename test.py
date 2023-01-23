import requests

r = requests.post('http://127.0.0.1/register-user', json={"name": "Jason Burne"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.get('http://127.0.0.1/get-users')
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.post('http://127.0.0.1/create-group', json={
  "id": "1",
          "groupName": "agents"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.get('http://127.0.0.1/get-groups')
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

r = requests.put('http://127.0.0.1/close-group', json={
  "username": "Jason Bourne",
          "groupName": "0"})
print(f"Status Code: {r.status_code}, Response: {r}, {r.content}")

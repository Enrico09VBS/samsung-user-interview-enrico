# Description

This is a set of CRUD APIs written in Java using Ratpack.

## How to Run

```bash
gradle run
```

## API Descriptions

```python
1. Get Object List
This API returns a list of undeleted objects from the database.
If there is no undeleted data in the database, it returns a string saying no data found.
Path: 'http://localhost:5050'
```

```python
2. Get Object by ID
This API returns a specific undeleted object from the database with the corresponding ID.
If there is no matching data in the database, it returns a string saying data not found.
Path: 'http://localhost:5050/:id'
```

```python
3. Insert Object
This API inserts data into the database.
It needs to receive the title, description, and status of the new object in the request parameters.
Returns true/false depending on if the process was successful or not
Path: 'http://localhost:5050/insert'
```

```python
4. Update Object
This API updates existing data in the database.
It needs to receive the title, description, and status of the new object in the request parameters.
Returns true/false depending on if the process was successful or not
Path: 'http://localhost:5050/update/:id'
```

```python
5. Delete Object
This API soft deletes existing data in the database.
Returns true/false depending on if the process was successful or not
Path: 'http://localhost:5050/delete/:id'
```
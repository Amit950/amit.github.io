# Employee Portal Prototype

This project contains a basic prototype for managing account holders. The front-end pages are plain HTML and JavaScript and the backend is implemented using Java Servlets.

## Modules

- **Employee Login** – `/login.html` posts to `/login` and validates fields.
- **Add Customer** – `/add_customer.html` collects customer information and posts to `/addCustomer`.
- **List & Edit Customers** – `/list_customers.html` lists existing customers and links to `/edit_customer.html` for edits.

Database connection details are configured in each servlet for a local MySQL instance. Update the JDBC URL, username and password as needed.

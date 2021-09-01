# Data model documentation

The goal of this document is to describe the meaning hidden behind the relational data model and how pieces of the
data model relate to each other.


# Tables

We describe the data model on table level

## Provider table

This table represents the service providers which can execute a service offered by the business. This entity represents
actual people which perform the services. For example if your business is a beauty saloon, then the actual employees
of the saloon would be represented by the entries in this table.

Note that only employees which actually provide the services are stored in this table. For example an employee which
is employed as beautician will be entered into this table, while the employee which is employed as saloon manager 
will not be entered into this table. 

Example entries:

| ID - serial | First name - varchar(128) | Last name - varchar(128)
|-------------|---------------------------|--------------------------
|   1         |  Vanio                    | Begic                   |
_____________________________________________________________________

This entry would represent a service provider with first name `Vanio` and last name `Begic`. No further information
about the service provider is contained in this table.

Constraints on this table:
- None of the columns can contain null values
- Entries in column `first_name` must be lowercase
- Entries in column `last_name` must be lowercase

This table is related to tables:
- Provider location time table

## Service table

Represents the services which may be provided by the providers entered into the `Provider` table. The entities of
this table represent the actual performed services by the providers. For example if your business is a beauty saloon
then an example service offered would be `Man's haircut`.

The data model currently assumes that all of services in `Service` are providable by all of the providers in the
`Provider` table.

Example entries:

ID - serial | service_price_id - integer | name - varchar(255) | description - text | duration_minutes 
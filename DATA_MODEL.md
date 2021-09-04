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

| ID - serial | service_price_id - integer | name - varchar(255) | description - text | duration_minutes - integer |
|-------------|----------------------------|---------------------|--------------------|----------------------------|
| 1           | 1                          | man's haircut       | Haircut for men    | 30                         |
-------------------------------------------------------------------------------------------------------------------

This entry represents a service with name `Man's haircut` whose price is determined by an entry in related table and 
which takes 30 minutes to execute. 

Constraints on the table:
- Each service must have a price, meaning that column `service_price_id` may not be null
- Each service must have a name, meaning that column `name` may not be null
- Each service must have a description, which means that column `description` may not be null
- Each service must have the duration specified, meaning that column `duration_minutes` may not be null
- The duration of the service is represented by positive integers ( > 0 )  
- Column `name` is unique in this table
- Column `name` must be lowercase. This has been implemented in order to avoid as much as possible entry of same exact
  service with names differing only in case used for specific letters.
- Column `service_price_id` is unique in this table, effectively meaning that each entry in `service_price_id` can be
  valid for only one service at a time. For example if there are two services `ServiceA` and `ServiceB` whose price is 
  `50$`, then there would have to exist two entries in the `service_price` table, each representing the price of 
  `ServiceA` and `ServiceB` respectively. We can not recycle the price entity across multiple services. Reason for this
  is to prevent accidental updates of prices for multiple non-related services. For example if both `ServiceA` and 
  `ServiceB` referred to the same `service_price` entity, then updating the row in `service_price` table could lead to
  unintended updates of prices for both of the services.
  
Regarding the relational design between tables `service` and `service_price`, we note that we need to establish a kind
of (strictly-one)-to-(strictly-one) between these two tables, since each of the entries in `service` table must have
a price associated, and we want to avoid accidental updates of prices across unrelated services, thus each of the prices
can only be related to only one of the services. We note that following designs have also been considered for this
relation:

- We could have designed the relation by having the table `service_price` reference the `service` table, but that would
  open up the possibility of having entries in `service` table which have no corresponding entry in the `service_price`.
  This effectively means that we could create `service` entities with no prices, which could be detrimental to user
  experience when such services are shown by error.
  
- Next candidate solution was to have a many-to-many join-table relating `service` instances to `service_price` instances,
  again this solution has the same weakness as the one in which `service_price` refers to `service` table.

This table is related to tables:
- `service_price`

## Service price table

Represents the price of the service executed by the provider. For example the price of the service is 30 dollars.
The service price is currently specified for currency `BAM` (Bosnian Convertible Mark) as number of whole units and 
number of units representing the 1/100-th part of the currency. 

Example entries:

| ID - serial | price_km - integer | price_fening - integer|
|-------------|--------------------|-----------------------|
|1            | 10                 | 50                    |
------------------------------------------------------------

The entry above would represent a service whose price is equal to `10.50 BAM`.

Constraints on table:
- None of the columns may contain nulls.
- The columns `price_km` and `price_fening` are non-negative integers ( >= 0 ).

This table is related to tables:
- `service`

## Location table

Represents the locations at which services can be provided. Locations are not specified as exact addresses, but rather
as simple identifiers of locations available. For example if your business has a subsidiary at the address
`5th Ave #101, New York, NY 10003, United States` then the location in this table would be simply `New York - Manhattan`.

Example entries:

| ID - serial | name - varchar(255) | 
|-------------|---------------------|
|1            | new york - manhattan|
-------------------------------------

Constraints on the table:

- None of the columns may be null
- The `name` column has to be lowercase in order to avoid as much as possible entering same locations with different
  names.
- The `name` column is unique


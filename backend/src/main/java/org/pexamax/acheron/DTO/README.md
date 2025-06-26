# What is DTO (Data Transfer Object)?

DTO helps transfer objects between different layers of an application, such as from the service layer to the controller layer and vice versa.

# Why do you need DTO when your entity already has all the fields and methods?

DTO allows developers to control what data is sent to the client, ensuring that only the necessary information is exposed.
This can help prevent over-fetching of data and can also enhance security by not exposing sensitive fields that are present in the entity.

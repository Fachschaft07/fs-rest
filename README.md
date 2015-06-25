# fs-rest
REST interface for several services

## Documentation
Read the [Wiki](https://github.com/Fachschaft07/fs-rest/wiki) for more information.

The project has 3 modules:

### Server
The Server module contains the REST-API and will be deployed.

### Common
The Common module contains different classes (most model and constants) which will be needed by the Server and Client module.

### Client
The Client module contains the interfaces to access the REST-API. This module (packaged as jar) can be used from other projects to access the REST-API. 

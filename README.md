fs-rest
======================

[![Build Status](https://travis-ci.org/Fachschaft07/fs-rest.svg)](https://travis-ci.org/Fachschaft07/fs-rest)
[![Coverage Status](https://coveralls.io/repos/Fachschaft07/fs-rest/badge.svg?branch=master&service=github)](https://coveralls.io/github/Fachschaft07/fs-rest?branch=master)

REST interface for several services

## Code Style Guide
Read the [Google Coding Style Guide](https://github.com/google/styleguide) for more information.

## Documentation
Read the [Wiki](https://github.com/Fachschaft07/fs-rest/wiki) for more information.

The project has 3 modules:

### Server
The Server module contains the REST-API and will be deployed.

### Common
The Common module contains different classes (most model and constants) which will be needed by the Server and Client module.

### Client
The Client module contains the interfaces to access the REST-API. This module (packaged as jar) can be used from other projects to access the REST-API. 

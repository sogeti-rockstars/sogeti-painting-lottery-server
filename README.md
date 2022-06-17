# sogeti-lotteryItem-lottery-server

### Notes

- CRUD operations have unreliable responses, use GET to update values after doing a CRUD...
- We had some trouble configuring hibernate or designing the schema. There are a couple of workarounds in the controller and service packages that resolve the issues we faced.
  No known bugs as of 2022-06-17 with the operations used by the client are known.
- Not all operations on REST are in use.

### application.properties
- Make sure to set the profile to production in pom.xml
- photobucket.path= is to be set to a path where photos are uploaded. Can be set to the string ###TEMPDIR### to use the
  OS temporary directory.
- When adding a lottery item without a photo, a placeholder is linked in to the photo directory. Apparently this wasn't
  working on windows without privilege escalation, it's tested and working on Linux and should work on OS X. This wasn't
  resolved due to time contraints and we're assuming the server will run on Linux.

### Building for production
NOT TESTED


### Paths descriptions
REQUEST PATH                                &emsp; => &emsp; ACTION/RESPONSE DESCIPTION

/api/v1/info                                &emsp; => &emsp; Editable fields, e.g. about us, assosiation leadership etc.

GET:  /api/v1/info                          &emsp; => &emsp; All fields. (Not used currently)

GET:  /api/v1/info/{id}                     &emsp; => &emsp; (Not used by client currently)

DEL:  /api/v1/info/{id}                     &emsp; => &emsp; (Not used by client currently)

POST: /api/v1/info                          &emsp; => &emsp; (Not used by client currently)

PUT:  /api/v1/info/{id}                     &emsp; => &emsp; (Not used by client currently)

GET:  /api/v1/info/field                    &emsp; => &emsp; All fields as key-value pairs

GET:  /api/v1/info/field/{fieldName}        &emsp; => &emsp; Value of field with key {fieldName}

DEL:  /api/v1/info/field/{fieldName}        &emsp; => &emsp; Value of field with key {fieldName}

PUT:  /api/v1/info/field/{fieldName}        &emsp; => &emsp; Value of field with key {fieldName}

POST: /api/v1/info/field/{fieldName}        &emsp; => &emsp; (Not used by client currently, the keys are hardcoded in typescript, only the values are user settable



PATH: /api/v1/users

GET:/api/v1/users/current                   &emsp; => &emsp; Currently validated user: ADMIN/ [EVERYONE ELSE]

PUT:/api/v1/users/logout                    &emsp; => &emsp; Invalidates jsession id

PUT:/api/v1/users/password                  &emsp; => &emsp; Changes the password which is stored encrypted in the database



PATH: /api/v1/contestant

GET:/api/v1/contestant                      &emsp; => &emsp; All contestants. Same regardless of lottery year chosen.

GET:/api/v1/contestant/{id}                 &emsp; => &emsp; Contestant by id

DEL:/api/v1/contestant/{id}                 &emsp; => &emsp; Contestant by id

PUT:/api/v1/contestant/{id}                 &emsp; => &emsp; Contestant by id

POST:/api/v1/contestant                     &emsp; => &emsp; Post new contestant



PATH: /api/v1/lottery

GET:/api/v1/lottery                         &emsp; => &emsp; All lotteries.

GET:/api/v1/lottery/summary                 &emsp; => &emsp; All lotteries where all fields except id and title have been stripped. Used by the topbar to allow switching of years.

GET:/api/v1/lottery/{id}                    &emsp; => &emsp; Specific lottery

DEL:/api/v1/lottery/{id}                    &emsp; => &emsp; Lottery with id

PUT:/api/v1/lottery/{id}                    &emsp; => &emsp; Lottery with id

POST:/api/v1/lottery                        &emsp; => &emsp; Lottery

GET:/api/v1/lottery/{id}/winners            &emsp; => &emsp; Winners for lottery with {id}

GET:/api/v1/lottery/{id}/items              &emsp; => &emsp; Lottery items for lottery with {id}

GET:/api/v1/lottery/{id}/available-items    &emsp; => &emsp; Lottery items for lottery with {id} that don't have a winner assigned to them.

PUT:/api/v1/lottery/{id}/spin               &emsp; => &emsp; Add a randomly selected contestant as a winner to the lottery with {id}


PATH: /api/v1/item

GET:/api/v1/item                            &emsp; => &emsp; All lottery items

GET:/api/v1/item/{id}                       &emsp; => &emsp; Lottery item with id

DEL:/api/v1/item/{id}                       &emsp; => &emsp; Lottery item with id

PUT:/api/v1/item/{id}                       &emsp; => &emsp; Lottery item with id

POST:/api/v1/item                           &emsp; => &emsp; Lottery item with id

GET:/api/v1/item/image/{id}                 &emsp; => &emsp; The image an item with id

PUT:/api/v1/item/update-image/{id}          &emsp; => &emsp; Add an image to the item with id. Needs to be called in addition to post.



PATH: /api/v1/winner

GET:/api/v1/winner                          &emsp; => &emsp; All winners (Not currently used by client)

GET:/api/v1/winner/{id}                     &emsp; => &emsp; Winner with id (Not currently used by client)

DEL:/api/v1/winner/{id}                     &emsp; => &emsp; Winner with id

PUT:/api/v1/winner/{id}                     &emsp; => &emsp; Winner with id (Not currently used by client)

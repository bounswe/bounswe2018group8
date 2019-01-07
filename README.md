# The GRATEFUL 8
Welcome to our repository.
![](https://i.imgur.com/layMbnv.jpg)
## Description
The main objective of the project is providing an environment for clients and freelancers to collaborate in a single platform. The clients may create their projects and jobs about programming, writing, design, development etc. and post the details on this platform to find  an  expert  on  such  topics.  The  freelancers  are  free  to  bid  on  these  projects until  a  certain deadline that is specifically determined by the client. After collecting these bids, the client may agree with one of these freelancers. The agreement includes details such as job completion deadline, total fee to be paid, payment options and so on. 

## API Endpoints

#### Authentication Endpoints
| HTTP Method | URI Path | Description | Role |
| --- | --- | --- | --- |
| POST | /login/ | Login with user credentials. | Guest, User |
| POST | /register/ | Create a new user accounts. | Guest, User |
| POST | /logout/ | Logout from the authenticated user account. | User |
| POST | /register/verify-email/ | Verify and activate user account. | Guest, User |
| POST | /password/change/ | Change the password of the authenticated user account. | User |
| POST | /password/reset/ | Reset the password of an user account. | Guest, User |
| POST | /password/reset/confirm/ | Confirm the password reset request. | Guest, User |

#### User Endpoints
| HTTP Method | URI Path | Description | Role |
| --- | --- | --- | --- |
| GET | /users/ | List all the users. | Guest, User |
| GET | /users/self/ | Retrieve the authenticated user. | User |
| GET | /users/self/:id/ | Retrieve a user by id. | Guest, User |
| GET | /users/search/:name/ | Search a user by name. | Guest, User |
| POST | /users/deposit/ | Deposit money on the authenticated user account. | User |
| PUT | /users/self/ | Update the authenticated user's details. | User |
| PATCH | /users/self/ | Partial update the authenticated user's details. | User |


#### Project Endpoints
| HTTP Method | URI Path | Description | Role |
| --- | --- | --- | --- |
| POST | /projects/ | Create a new project. | User |
| GET | /projects/ | List all projects. | Guest, User |
| GET | /projects/:id/ | Retrieve a project by id. | Guest, User |
| PUT | /projects/:id/ | Update a project by id. | User |
| PATCH | /projects/:id/ | Partial update a project by id. | User |
| DELETE | /projects/:id/ | Delete a project by its id. | User |
| POST | /project/:id/bid/ | Make bid on a project by the authenticated user. | User |
| POST | /projects/:id/bid/accept/ | Accept a bid for the project by the authenticated user. | User |
| GET | /projects/search/:keyword/ | Search projects by keyword. | Guest, User |
| GET | /projects/user/:id/ | List projects of a specific user by id. | Guest, User |
| GET | /projects/user/self/ | List projects of the authenticated user. | User |


## The Team


[![Orkan Akısu](https://avatars0.githubusercontent.com/u/36167517?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/Orkan-Ak%C4%B1s%C3%BC) | [![Semih Arı](https://avatars0.githubusercontent.com/u/36154366?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/Semih-Ar%C4%B1) | [![İsmail Levent Baş](https://avatars0.githubusercontent.com/u/17166724?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/%C4%B0smail-Levent-Ba%C5%9F) | [![Abdullah Coşkun](https://avatars3.githubusercontent.com/u/32523435?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/Abdullah-Co%C5%9Fkun) | [![İsmet Dağlı](https://avatars1.githubusercontent.com/u/21147014?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/%C4%B0smet-Da%C4%9Fl%C4%B1) | [![Kübra Eryılmaz](https://avatars3.githubusercontent.com/u/34382537?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/K%C3%BCbra-Ery%C4%B1lmaz) | [![Abdurrahim Eskin](https://avatars1.githubusercontent.com/u/35101427?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/Abdurrahim-ESK%C4%B0N)  | [![Ahmet Selim Karaduman](https://avatars2.githubusercontent.com/u/26579971?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/Selim-Karaduman) | [![Yaman Kındap](https://avatars0.githubusercontent.com/u/32527700?s=400&v=4)](https://github.com/bounswe/bounswe2018group8/wiki/Yaman-K%C4%B1ndap)| [![Eray Sezgin](https://avatars1.githubusercontent.com/u/15183812?s=400&v=4)](https://github.com/sezgineray)
---|---|---|---|---|---|---|---|---|---
[Orkan Akısü](https://github.com/bounswe/bounswe2018group8/wiki/Orkan-Ak%C4%B1s%C3%BC) | [Semih Arı](https://github.com/bounswe/bounswe2018group8/wiki/Semih-Ar%C4%B1) | [İ.Levent Baş](https://github.com/bounswe/bounswe2018group8/wiki/%C4%B0smail-Levent-Ba%C5%9F) | [Abdullah Coşkun](https://github.com/bounswe/bounswe2018group8/wiki/Abdullah-Co%C5%9Fkun) | [İsmet Dağlı](https://github.com/bounswe/bounswe2018group8/wiki/%C4%B0smet-Da%C4%9Fl%C4%B1) | [Kübra Eryılmaz](https://github.com/bounswe/bounswe2018group8/wiki/K%C3%BCbra-Ery%C4%B1lmaz) | [Abdurrahim Eskin](https://github.com/bounswe/bounswe2018group8/wiki/Abdurrahim-ESK%C4%B0N) | [A.Selim Karaduman](https://github.com/bounswe/bounswe2018group8/wiki/Selim-Karaduman) | [Yaman Kındap](https://github.com/bounswe/bounswe2018group8/wiki/Yaman-K%C4%B1ndap) | [Eray Sezgin](https://github.com/sezgineray)


#
You can find more details about us on our [wiki page](https://github.com/bounswe/bounswe2018group8/wiki).



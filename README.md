<h1>Football System Management</h1>
<p>This project implements a comprehensive football system management application using a layered architecture:</p>
<h4>Backend (Spring Boot + MongoDB/PostgreSQL):</h4>
<ul>
  <li>Handles data persistence and business logic.</li>
  <li>Offers a RESTful API for interactions with the Angular frontend.</li>
  <li>Provides a choice between NoSQL (MongoDB) and SQL (PostgreSQL) databases based on project's specific needs.</li>
</ul>
<h4>Frontend (Angular):</h4>
<ul>
  <li>Provides a user-friendly interface for managing players, clubs, leagues, transfers, and standings.</li>
  <li>Utilizes Angular's reactive forms for robust data binding and validation.</li>
  <li>Leverages services to interact with the backend API.</li>
</ul>
<h4>Authorization and Authentication:</h4>
<ul>
  <li>Implements a secure JWT (JSON Web Token) based authentication flow.</li>
  <li>Generates refresh tokens for long-lived sessions and access tokens for API access.</li>
</ul>
<h4>Real-time Notifications (WebSockets):</h4>
<ul>
  <li>Enables real-time updates on events like asking for permissions for deleting players.</li>
  <li>Utilizes WebSockets for bi-directional communication between the server and client.</li>
</ul>
<h4>Elasticsearch:</h4>
<ul>
  <li>Allows users to quickly search for users, leagues, or other relevant entities.</li>
  <li>Filters rounds based on start and end date.</li>
  <li>Sorts players based on height and weight.</li>
</ul>
<h3>Modules</h3>
<hr/>
<ul>
  <li><strong>Player Management</strong></li>
  <ul>
    <li list-style-type: circle>
      The Player Management System provides a comprehensive solution tailored specifically for football clubs, enabling streamlined management of player profiles, statistics, and administrative tasks.
    </li>
     <li list-style-type: circle>
      Create and maintain detailed profiles for each player, including personal information (name, date of birth, nationality) and physical attributes (height, weight, preferred foot). (A CRUD)
    </li>
     <li list-style-type: circle>
    Capture player statistics such as match appearances, goals scored, assists, and disciplinary records.
    </li> 
     <li list-style-type: circle>
    Manage player contracts, including contract start and end dates, salary details, bonuses, and release clauses.
    </li>  
     <li list-style-type: circle>
    Track contract renewal dates and negotiate new terms with players and agents.
    </li>  
     <li list-style-type: circle>
    Record and monitor player injuries, rehabilitation progress, and medical history.
    </li>  
     <li list-style-type: circle>
    Generate reports on injury incidence and recovery timelines to inform training and team selection.
    </li>  
     <li list-style-type: circle>
    CRUD for Contract, every player should have one contract.
    </li>
  </ul>
  <br/>
  <li><strong>Club Management</strong></li>
   <ul>
      <li list-style-type: circle>Every user can register as a club, but to be able to log in it will need permission from Administrator.</li>
      <li list-style-type: circle>It must be able to give permission to user clubs, and delete them.</li>
      <li list-style-type: circle>It must be able to show users in a pageable list.</li>
      <li list-style-type: circle>It must be able to edit club info profile.</li>
  </ul>
  <br/>
  <li><strong>League Management</strong></li>
   <ul>
    <li list-style-type: circle>This section allows you to manage leagues within your Football System.</li>
      <li list-style-type: circle>Create League: Define a new League by specifying a name, region, and other relevant details (e.g., number of teams, promotion/relegation rules).</li>
     <li list-style-type: circle> You can set up League schedules and assign teams to participate.</li>
      <li list-style-type: circle>Read Leagues: View a list of all existing Leagues within the system.</li>
      <li list-style-type: circle>Access detailed information about each League, including its name, region, teams, schedule, standings, and history.</li>
      <li list-style-type: circle>Update League: Modify existing League details ex. name.</li>
      <li list-style-type: circle>Edit League schedules or assigned teams.</li>
      <li list-style-type: circle>Delete League: Remove a League from the system entirely.</li>
    </li>
  </ul>
  <br/>
  <li><strong>User Authentication and Authorization</strong></li>
   <ul>
    <li list-style-type: circle>
      This module handles user authentication, registration, and permissions. It allows different users such as administrators, clubs to access the system with appropriate privileges.</li>
      <li list-style-type: circle>It must contain a register for users with role club to be registered.</li>
      <li list-style-type: circle>It must contain a login to be logged in for users.  </li>
     <li list-style-type: circle> It must be able to edit its own info.</li>
      <li list-style-type: circle>It doesn’t allow administrator to change roles or something else.</li>
    </li>
  </ul>
  <br/>
    <li><strong>Season Management</strong></li>
   <ul>
    <li list-style-type: circle>
     Season Management allows you to control Seasons within each League in your Football System.</li>
    <li list-style-type: circle>Create Season: Define a new Season within a specific League.</li>
  <li list-style-type: circle>  Read Seasons: View a list of all Seasons within a particular League. </li>
    <li list-style-type: circle>Access details about each Season, including its name, start and end dates, participating teams, match schedules, standings, and historical data (e.g., top scorers, champions).</li>
    <li list-style-type: circle>Update Season: Modify details of an existing Season, such as start/end dates or participating teams. (Note: Changes might impact fixtures and results).</li>
    <li list-style-type: circle>Delete Season: Remove a Season entirely from the League.</li>
    <li list-style-type: circle>A CRUD for Matches.</li>
  <li list-style-type: circle>  A CRUD for Rounds.</li>
   <li list-style-type: circle> It must generate 5 matches for round, based on  how many times they will play using a special algorithm for it.</li>
    <li list-style-type: circle>A CRUD for Match Event. </li>
    <li list-style-type: circle>Through Season Management (CRUD), we can efficiently organize and manage Seasons within our Leagues, ensuring a well-structured and informative Football System.</li>
    </li>
  </ul>
</ul>
<h3>Prerequisites</h3>
<hr/>
<ul>
  <li>Java 17+</li>
  <li>Maven</li>
  <li>Node.js and npm</li>
  <li>MongoDB and PostgreSQL</li>
  <li>An IDE (e.g., Intellij IDEA)</li>
</ul>
<h3>Setup</h3>
<hr/>
<h4>Backend (Spring Boot)</h4>
<ol>
  <li>Clone this repository</li>
  <li>Configure connection details in application.properties.</li>
  <li>Run mvn clean install to build.</li>
  <li>Run mvn spring-boot:run to start the server.</li>
</ol>
<h4>Frontend (Angular)</h4>
<ol>
  <li>Navigate to the frontend directory.</li>
  <li>Run npm install (or yarn install) to install dependencies.</li>
  <li>Run ng serve to start the Angular development server.</li>
</ol>
<h4>Access the Application</h4>
<ul>
  <li>Backend server: Typically port 8080 (adjust if needed).</li>
  <li>Angular application: Usually accessible at http://localhost:4200 in your browser.</li>
</ul>
<h3>Team</h3>
<hr/>
<ol>
  <li>Andi Gela</li>
  <li>Dren Halili</li>
  <li>Elsa Krasniqi</li>
  <li>Vesa Fetahaj</li>
</ol>
<h3>Overview of system</h3>
<hr/>

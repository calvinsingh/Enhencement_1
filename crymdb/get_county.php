<?php
  //get db file containing conn config
  include 'database.php';
  //select all chat data from db 
  $sql_fetch_county_crime = $connection->prepare("SELECT * FROM counties ORDER BY crime_count DESC");
  $sql_fetch_county_crime->execute();
  //fetching results
  $get_result = $sql_fetch_county_crime->get_result();
  $county_crime_output = $get_result->fetch_all(MYSQLI_ASSOC);
  //displaying fetched data in java script object notation(json) ie name:value eg "average":"21.45"
  echo json_encode($county_crime_output);
  
?>
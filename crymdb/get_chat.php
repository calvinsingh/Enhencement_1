<?php
  //get db file containing conn config
  include 'database.php';
  //select all chat data from db 
  $sql_fetch_reported = $connection->prepare("SELECT * FROM chat");
  $sql_fetch_reported->execute();
  //fetching results
  $get_result = $sql_fetch_reported->get_result();
  $reported_crime_output = $get_result->fetch_all(MYSQLI_ASSOC);
  //displaying fetched data in java script object notation(json) ie name:value eg "average":"21.45"
  echo json_encode($reported_crime_output);
?>
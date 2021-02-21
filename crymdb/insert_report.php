<?php
  include 'database.php';

  if(isset($_POST['title']) && isset($_POST['county']) && isset($_POST['crime_description']) && isset($_POST['estate'])){
    $title = $_POST['title'];
    $county = $_POST['county'];
    $description = $_POST['crime_description'];
    $estate = $_POST['estate'];
    
    $sql_county_crime=$connection->query("SELECT * FROM counties WHERE name = '$county' ");
    //fetch
    $res_county_crime=mysqli_fetch_assoc($sql_county_crime);
    $no_crime=intval($res_county_crime['crime_count']);
    //new crime total
    $no_crime += 1; 

    if(!empty($title) || !empty($county || !empty($description) || !empty($estate))){
      $sql_insert_report = $connection->prepare("INSERT INTO reported(title, county, description, estate) VALUES('$title', '$county', '$description', '$estate')");

      $sql_update_county_crime=$connection->query("UPDATE counties SET crime_count='$no_crime' WHERE name='$county'");

      //record count in coutines table
      //check if insert successfull
      if($sql_insert_report->execute()){
        echo "Crime reported successfully";
      }else{
        echo "Report insertion error";
      }
    }else{
      echo "All fields are required";
    }
  }else{
    echo "All fields are required 2";
  }
  
?>
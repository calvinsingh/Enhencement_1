<?php
  include 'database.php';

  //get values
  ;

  if(isset($_POST['comment'])){  
    $comment=$_POST['comment'];
    if(empty($comment)){
      //please leavechat
      echo "Please fill to chat";
    }else {
      # code...
      $sql_insert_chat = $connection->prepare("INSERT INTO chat(comment) VALUES('$comment') ");
      //check if insert successfull
      if($sql_insert_chat->execute()){
        echo "Chat inserted";
      }else{
        echo "Chat insertion error";
      }
    }
  }
  
?>
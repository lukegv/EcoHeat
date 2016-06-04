<?php

ini_set("display_errors", 1);
ini_set("track_errors", 1);
ini_set("html_errors", 1);
ini_set("log_errors", 1);
error_reporting(E_ALL);


$servername = "########";
$username = "########";
$password = "########";
$dbname = "HackathonViessmann_DB";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 


// $csv = array_map('str_getcsv', file('Anlage1.csv'));

$houseid = 5;
$filename = 'Anlage5.csv';

$file = file_get_contents($filename);
$data = str_getcsv($file, "\n"); //parse the rows 
// $i = 0;
foreach($data as &$row) {
	$row = str_getcsv($row, ";"); //parse the items in rows 
	
	// $i+=1;
	
	// if($i == 1)
		// continue;
	
	// print_r($row);
	// break;
	
	
	$timestamp = $row[0];
	$temp_outdoor = $row[1];
	$temp_indoor = $row[2];
	$energy = $row[3];
	
	$query = "INSERT INTO viess_history (houseid, timestamp, temp_indoor, temp_outdoor, energy)
VALUES ('$houseid','$timestamp','$temp_indoor','$temp_outdoor','$energy');";

	// echo $query."<br>";
	if ($conn->query($query) === TRUE) {
		echo "New record created successfully";
	} else {
		echo "Error: " . $query . "<br>" . $conn->error;
		break;
	}

}


// print_r($csv);


// $sql = "SELECT * FROM viess_houses WHERE user = '$app_user'";
// $result = $conn->query($sql);


$conn->close();

echo "done";
?>

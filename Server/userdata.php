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

$app_user = $_GET['user'];



$sql = "SELECT * FROM viess_houses WHERE user = '$app_user'";
$result = $conn->query($sql);

// if ($result->num_rows > 0) {
    // output data of each row
    // while($row = $result->fetch_assoc()) {
        // echo "id: " . $row["user"]. " - Name: " . $row["location"]. " " . $row["housename"]. "<br>";
    // }
// } else {
    // echo "0 results";
// }

// time step length, needs to be included in factors
$timefactor = 3600/30; // from kWh to kJ with timestep 30sec


$xml = new XMLWriter();

$xml->openURI("php://output");
$xml->startDocument();
$xml->setIndent(true);

$xml->startElement('userdata');

while ($row = $result->fetch_assoc()) {
  $xml->writeAttribute('name', $row['user']);
  $xml->writeAttribute('houseid', $row['houseid']);
  $xml->writeAttribute('const_prop', $timefactor*$row['const_prop']);
  $xml->writeAttribute('const_deriv', $timefactor*$row['const_deriv']);
  $xml->writeAttribute('location', $row['location']);
  $xml->writeAttribute('longlat', $row['longlat']);
  $xml->writeAttribute('housename', $row['housename']);
  $xml->writeAttribute('score', $row['score']);
	
  // $xml->startElement("name");
  // $xml->writeRaw($row['user']);
  // $xml->endElement();

  // $xml->startElement("houseid");
  // $xml->writeRaw($row['houseid']);
  // $xml->endElement();
  
  // $xml->startElement("const_prop");
  // $xml->writeRaw($row['const_prop']);
  // $xml->endElement();

  // $xml->startElement("const_deriv");
  // $xml->writeRaw($row['const_deriv']);
  // $xml->endElement(); 
  
  // $xml->startElement("location");
  // $xml->writeRaw($row['location']);
  // $xml->endElement();   
  
  // $xml->startElement("longlat");
  // $xml->writeRaw($row['longlat']);
  // $xml->endElement(); 

  // $xml->startElement("housename");
  // $xml->writeRaw($row['housename']);
  // $xml->endElement();   
  }

$xml->endElement();

header('Content-type: text/xml');
$xml->flush();


$conn->close();
<?php
header('Content-type: text/xml');

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

$houseid = $_GET['houseid'];

$timestep = 10; // minutes

$selectcount = 6*5*24;

$sql = "SELECT * FROM viess_history WHERE houseid = '$houseid' ORDER BY timestamp DESC LIMIT $selectcount";
$result = $conn->query($sql);

$xml = new XMLWriter();

$xml->openURI("php://output");
$xml->startDocument();
$xml->setIndent(true);

$xml->startElement('history');

while ($row = $result->fetch_assoc()) {
	
  $xml->startElement("entry");
  $xml->writeAttribute('timestamp', $row['timestamp']);
  $xml->writeAttribute('temp_indoor', $row['temp_indoor']);
  $xml->writeAttribute('temp_outdoor', $row['temp_outdoor']);
  $xml->writeAttribute('energy', $row['energy']);
  $xml->endElement();
  }

$xml->endElement();

$xml->flush();

$conn->close();
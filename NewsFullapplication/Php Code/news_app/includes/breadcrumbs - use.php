<?php

/*** a new breadcrumbs object ***/
$bc = new breadcrumbs;

/*** set the pointer if you like ***/
$bc->setPointer('->');

/*** create the trail ***/
$bc->crumbs();

/*** output ***/
echo $bc->breadcrumbs;

?>
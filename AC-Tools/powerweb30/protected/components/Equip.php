<?php

class Equip extends Controller
{
	function image($item, $slot) {
		if($slot == '1') return '<div style="position:absolute; top:-1px; left:33px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '2') return '<div style="position:absolute; top:-1px; left:157px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '4') return '<div style="position:absolute; top:40px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '8') return '<div style="position:absolute; top:125px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '16') return '<div style="position:absolute; top:166px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '32') return '<div style="position:absolute; top:248px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '64') return '<div style="position:absolute; top:83px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '128') return '<div style="position:absolute; top:83px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '256') return '<div style="position:absolute; top:207px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '512') return '<div style="position:absolute; top:207px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '1024') return '<div style="position:absolute; top:40px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '2048') return '<div style="position:absolute; top:125px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '4096') return '<div style="position:absolute; top:166px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '8192') return '<div style="position:absolute; top:-20px; left:118px;" class="item"><a class="aion-item-icon-medium" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '16384') return '<div style="position:absolute; top:-20px; left:68px;" class="item"><a class="aion-item-icon-medium" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '32768') return '<div style="position:absolute; top:15px; left:95px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '65536') return '<div style="position:absolute; top:248px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '131072') return '<div style="position:absolute; top:-20px; left:14px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '262144') return '<div style="position:absolute; top:-20px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		elseif($slot == '524288') return '<div style="position:absolute; top:-20px; left:177px;" class="item"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/'.$item.'" target="_blank"></a></div>';
		else return '';
	}
}







<script>
function add(e){
	/*var amount = document.getElementById(e).value;
	document.getElementById('Inventory_new_amount').value = amount;
	document.getElementById("amount").innerHTML = amount;
	
	var real = document.getElementById("r"+e).value;
	var prices = document.getElementById("p"+e).innerHTML;
	price = parseInt(prices)
	document.getElementById("new_price").innerHTML = amount * price / real;*/
	//alert(e);
	$.cookie("value", e, {expires: 1});
}
</script>


<a href="http://www.gtop100.com/in.php?site=39714" target="_blank" onclick="add('gtop100')" >Basic</a>

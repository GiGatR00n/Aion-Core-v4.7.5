






<script>
function remount(e){
	var amount = document.getElementById(e).value;
	document.getElementById('Inventory_new_amount').value = amount;
	document.getElementById("amount").innerHTML = amount;
	
	var real = document.getElementById("r"+e).value;
	var prices = document.getElementById("p"+e).innerHTML;
	price = parseInt(prices)
	document.getElementById("new_price").innerHTML = amount * price / real;
}
</script>

<div class="note">
	<div class="note_title">
		<a href="../webshop">Webshop</a> &rarr; <?php echo $id->name; ?>
	</div>
	<div class="note_body">
		<?php if (Yii::app()->user->hasFlash('message')) echo Yii::app()->user->getFlash('message'); ?>
		<div class="form"><?php echo CHtml::errorSummary($form)?></div>
		<table class="items">
			<tr>
				<th colspan="2">Title</td>
				<th>Level</td>
				<th>Qty</td>
				<th>Price</td>
				<th>Purchase</td>
			</tr>
		<?php $form=$this->beginWidget('CActiveForm', array('id'=>'inventory-form',)); ?>
		<?php foreach ($model as $model): ?>
			<tr class="center">
				<td width="45px" align="left"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/<?php echo $model->item_id;?>" target="_blank"></a></td>
				<td align="left"><?php echo $model->name; ?></td>
				<td><?php echo $model->level; ?></td>
				<td>
					<input size="1" type="text" <?php if ($model->edit == 0) echo 'disabled'; ?> id="<?php echo $model->item_id; ?>" name="Inventory[item_count]" value="<?php echo $model->amount; ?>" onblur="remount(<?php echo $model->item_id; ?>)" />
					<input type="hidden" id="r<?php echo $model->item_id; ?>" value="<?php echo $model->amount; ?>" />
				</td>
				<td><span id="p<?php echo $model->item_id; ?>"><?php echo $model->price; ?></span></td>
				<td><input type="radio" id="Inventory[item_id]" name="Inventory[item_id]" value="<?php echo $model->item_id; ?>" onclick="remount(<?php echo $model->item_id; ?>)"  /></td>
			</tr>
		<?php endforeach; ?>
			<tr>
				<td colspan="2" align="right">
					Qty: <span id="amount">0</span>, price: <span id="new_price">0</span>
				</td>
				<td colspan="2" align="center">
					<select name="Inventory[item_owner]" id="Inventory_item_owner">
						<?php foreach ($players as $player): ?>
						<OPTION VALUE="<?php echo $player->id; ?>"><?php echo $player->name; ?></OPTION>
						<?php endforeach; ?>
					</select>
				</td>
				<td colspan="2" align="center">
					<input type="hidden" id="Inventory_new_amount" name="Inventory[new_amount]" value="" />
					<input class="button" type="submit" value="Purchase">
				</td>
			</tr>
		<?php $this->endWidget(); ?>
		</table>
	</div>
</div>

<div class="pages">
	<?php $this->widget('CLinkPager',array(
		'pages'=>$pages,
		'maxButtonCount' => 7,
		'nextPageLabel' => '&rarr;',
		'prevPageLabel' => '&larr;',
		'firstPageLabel' => '<<',
		'lastPageLabel' => '>>',
		'header' => '',
	)); ?>
</div>
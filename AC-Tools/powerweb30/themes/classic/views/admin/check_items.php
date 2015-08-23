






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Checking fused items...
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Character</th>
				<th>Inventory ID</th>
				<th>Item ID</th>
				<th>Extra item ID</th>
				<th>Delete</th>
			</tr>
		<?php foreach($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'player/'.$data->players->name; ?>"><?php echo $data->players->name ;?></a></td>
				<td><?php echo $data->item_unique_id ;?></td>
				<td><?php echo $data->item_id ;?></td>
				<td><?php echo $data->fusioned_item ;?></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'admin/check/idelete/'.$data->item_unique_id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Delete" /></a></td>
			</tr>
		<?php endforeach; ?>
		</table>
	</div>
</div>
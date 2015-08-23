






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Report - Webshop Items
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th colspan=2>Add Items</td>
				<th>Character</td>
				<th>Qty</td>
				<th>Price</td>
				<th>Date</td>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td width="45px" align="left"><a class="aion-item-icon-large" href="http://www.aiondatabase.com/item/<?php echo $data->item; ?>" target="_blank"></a></td>
				<td align="left"><a class="aion-item-text" href="http://www.aiondatabase.com/item/<?php echo $data->item; ?>"></a></td>
				<td><?php echo $data->players->name; ?></td>
				<td><?php echo $data->amount; ?></td>
				<td><?php echo $data->price; ?></td>
				<td><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
			</tr>
		<?php endforeach; ?>
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
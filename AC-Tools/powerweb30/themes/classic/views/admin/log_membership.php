






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Report - Privileges purchased
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Account</th>
				<th>Title</th>
				<th>Type</th>
				<th>Duration (days)</th>
				<th>Price</th>
				<th>Date</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->account; ?></td>
				<td><?php echo $data->title; ?></td>
				<td><?php echo $data->type; ?></td>
				<td><?php echo $data->duration; ?></td>
				<td><?php echo $data->price; ?></td>
				<td width="150px"><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
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
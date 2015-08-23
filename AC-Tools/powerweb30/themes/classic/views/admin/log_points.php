






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Report - Points transferred
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Sender</th>
				<th>Recipient</th>
				<th>Total</th>
				<th>Date</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->sender; ?></td>
				<td><?php echo $data->recipient; ?></td>
				<td><?php echo $data->sum; ?></td>
				<td width="180px"><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
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
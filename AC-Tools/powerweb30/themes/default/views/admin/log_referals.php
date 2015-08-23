






<div class="note">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; Report - Referrals made
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>User</th>
				<th>Referral</th>
				<th>Date</th>
				<th>Status</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><?php echo $data->master; ?></td>
				<td><?php echo $data->slave; ?></td>
				<td width="150px"><?php echo Yii::app()->dateFormatter->format('dd.MM.y, HH:mm:ss', $data->date); ?></td>
				<td><?php echo Info::status($data->status); ?></td>
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







<div class="note mb15">
	<div class="note_title">
		<a href="<?php echo Yii::app()->homeUrl; ?>admin">Admin area</a> &rarr; List of pages
	</div>
	<div class="note_body">
		<table class="items">
			<tr>
				<th>Page name</th>
				<th>Page title</th>
				<th colspan="2">Edit/delete</th>
			</tr>
		<?php foreach ($model as $data): ?>
			<tr class="center">
				<td><a href="<?php echo Yii::app()->homeUrl.'page/'.$data->name; ?>"><?php echo $data->name; ?></a></td>
				<td align="left"><?php echo $data->title; ?></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'page/edit/'.$data->id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/edit.png" title="Edit" /></a></td>
				<td width="36px"><a href="<?php echo Yii::app()->homeUrl.'page/delete/'.$data->id; ?>" /><img src="<?php echo Yii::app()->homeUrl; ?>images/delete.png" title="Delete" /></a></td>
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
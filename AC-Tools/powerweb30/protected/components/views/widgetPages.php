<ul class="menu_link">
<?php foreach($model as $data): ?>
	<li><a href="<?php echo Yii::app()->homeUrl.'page/'.$data->name; ?>" title="<?php echo $data->title; ?>"><?php echo $data->title; ?></a>
<?php endforeach; ?>
</ul>
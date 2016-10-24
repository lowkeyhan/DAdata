//夜间模式
		
		var yjoption = {
			backgroundColor: '#404a59',
			tooltip: {
			        backgroundColor: '#222',
			        borderColor: '#777',
			        borderWidth: 1,
			        textStyle:{
						color:'#fff',
						fontSize:12,
					},
			    },
			xAxis: {
		        axisLine: {
		            lineStyle: {
		                color: '#eee'
		            }
		        }
		    },
		    yAxis: {
		        axisLine: {
		            lineStyle: {
		                color: '#eee'
		            }
		        }
		    },
		    series: [{
				label: {
					normal: {
						show: true,
						textStyle: {
							fontSize: 10,
							//泡泡字体颜色
							color: '#fff'
						}
					},

				},
			}]
		};
		//夜间模式结束
		//日间模式
		var rjoption = {
			backgroundColor: '#fbf9fe',
			tooltip: {
			        backgroundColor: '#fff',
			        borderColor: '#777',
			        borderWidth: 1,
			        textStyle:{
						color:'#000',
						fontSize:12,
					},
			    },
			xAxis: {
		        axisLine: {
		            lineStyle: {
		                color: '#000'
		            }
		        }
		    },
		    yAxis: {
		        axisLine: {
		            lineStyle: {
		                color: '#000'
		            }
		        }
		    },
		    series: [{
				label: {
					normal: {
						show: true,
						textStyle: {
							fontSize: 10,
							//泡泡字体颜色
							color: '#000'
						}
					},

				},
			}]
		};
		//日间模式结束
		
/*var schema = [{
			name: 'date',
			index: 0,
			text: '日'
		}, {
			name: 'cjl',
			index: 1,
			text: '采集率'
		}, {
			name: 'area',
			index: 2,
			text: '地区'
		}];
*/
		var itemStyle = {
			normal: {
				opacity: 0.8,
				/*shadowBlur: 10,
				shadowOffsetX: 0,
				shadowOffsetY: 0,
				shadowColor: 'rgba(0, 0, 0, 0.5)'*/
			}
		};

		
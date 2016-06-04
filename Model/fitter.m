
close all



n_total=size(outdoor_temperature_celsius,1); 
n = n_total;

% reduction factor
redFac = 120;       % 2 samples/minute * 120 = 1sample/hour

% reduce sampling resolution
indoorTred = mean(reshape(indoor_target_temperature_celsius, redFac, []))';
outdoorTred = mean(reshape(outdoor_temperature_celsius, redFac, []))';
energyRed = sum(reshape(energy_consumption_kwh, redFac, []))';

% e = energy_consumption_kwh(1:n);
% out = outdoor_temperature_celsius(1:n);
% in = indoor_target_temperature_celsius(1:n);

% initial guess for a and b 
x0 = [1,1]';

[x,fval,exit] = fminunc(@(x) fun(x(1),x(2),energyRed,indoorTred,outdoorTred), x0);
a = x(1)
b = x(2)

energyEstRed = Q(a,b,indoorTred,outdoorTred);
total_energy_opt = sum(energyEstRed)
total_energy_real = sum(energy_consumption_kwh)


%% visualization


times = (1:n)*30/3600;
timesRed = mean(reshape(times, redFac, []))';

% integral length
chunksize = 2*3600/30;

energy_integral = integr(energy_consumption_kwh, chunksize);
% energy_est_integr = integr(energy, chunksize);


% for visualization
n=5*2880;

% draw
figure 
line(times(1:n),outdoor_temperature_celsius(1:n),'Color','r')
line(times(1:n),indoor_target_temperature_celsius(1:n),'Color','b')
% line(times(1:n),tempdiff_integral(1:n),'Color','g')
ax1 = gca; % current axes
ax1.XColor = 'r';
ax1.YColor = 'r';
xlabel('Time [h]');
ylabel('Temperature [C]');

ax1_pos = ax1.Position; % position of first axes
ax2 = axes('Position',ax1_pos,...
    'XAxisLocation','top',...
    'YAxisLocation','right',...
    'Color','none');
ylabel('Energy Consumption [kWh]');

% factor = mean_point*delta_t;

% line(times(1:n),energy_integral(1:n),'Parent',ax2,'Color','k')
line(timesRed(1:n/redFac),energyEstRed(1:n/redFac),'Parent',ax2,'Color','g')
line(timesRed(1:n/redFac),energyRed(1:n/redFac),'Parent',ax2,'Color','k')
% line(times(1:n),tempdiff_deriv_integral(1:n),'Parent',ax2,'Color','y')

%%
in = ones(n_total,1)*max(indoor_target_temperature_celsius);

energy = Q(a,b,in,out);
total_energy_nopattern = sum(energy)
% plot(energy)

energy_savings = 1-total_energy_real/total_energy_nopattern

close all
n=2880*5;
plot(times(1:n),energy_consumption_kwh(1:n),times(1:n),energy(1:n))